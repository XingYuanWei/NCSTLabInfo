package temp;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.javatuples.Triplet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import java.io.File;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 临时工具，将学院和专业信息存入数据库
 */
@ContextConfiguration("classpath:spring-tools.xml")
public class ImportCollegeProfessionListFromExcel extends AbstractTestNGSpringContextTests {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Test
    public void doImportCollegeList() throws Exception {
        XSSFWorkbook excel = new XSSFWorkbook(new File("_文档/学院与专业.xlsx"));
        XSSFSheet mainSheet = excel.getSheetAt(1);
        XSSFRow row;
        XSSFCell cell;
        Iterator<Row> rows = mainSheet.iterator();
        rows.next();
        LinkedList<String> collegeList = new LinkedList<>();
        while(rows.hasNext()) {
            row = (XSSFRow) rows.next();
            cell = row.getCell(1);
            String collegeName = cell.getStringCellValue();
            if (!collegeList.contains(collegeName)) {
                collegeList.add(collegeName);
            }
        }
        collegeList.sort(new PinyinComparator());
        System.out.println(collegeList);
        for (String collegeName : collegeList) {
            insertCollegeName(collegeName);
        }
    }

    @Test
    public void doImportProfessionList() throws Exception {
        XSSFWorkbook excel = new XSSFWorkbook(new File("_文档/学院与专业.xlsx"));
        XSSFSheet mainSheet = excel.getSheetAt(1);
        XSSFRow row;
        Iterator<Row> rows = mainSheet.iterator();
        rows.next();
        //collegeName, ProfessionName, ProfessionDomain(nullable)
        LinkedList<Triplet<String, String, String>> professionDataList = new LinkedList<>();
        while(rows.hasNext()) {
            row = (XSSFRow) rows.next();
            Cell cellAssertDomain = row.getCell(3);
            Integer assertDomain  = (int) cellAssertDomain.getNumericCellValue();
            Cell cellProfessionName = row.getCell(2);
            String professionName = cellProfessionName.getStringCellValue();
            Cell cellCollegeName = row.getCell(1);
            String collegeName = cellCollegeName.getStringCellValue();
            String professionDomain = null;
            if (assertDomain.equals(1)) {
                Pattern pattern = Pattern.compile("(.+)（(.+)）");
                Matcher matcher = pattern.matcher(professionName);
                if (matcher.matches()) {
                    professionDomain = matcher.group(2);
                }
            }
            professionDataList.add(new Triplet<>(collegeName, professionName, professionDomain));
        }
        professionDataList.sort(new ProfessionDataComparator());
        System.out.println(professionDataList);
        for (Triplet<String, String, String> curr : professionDataList) {
            insertProfessionData(curr);
        }
    }

    class ProfessionDataComparator implements Comparator<Triplet<String, String, String>> {
        PinyinComparator pinyinComparator = new PinyinComparator();

        @Override
        public int compare(Triplet<String, String, String> o1, Triplet<String, String, String> o2) {
            int collegeCompare = pinyinComparator.compare((String) o1.getValue(0), (String) o2.getValue(0));
            int professionCompare = pinyinComparator.compare((String) o1.getValue(1), (String) o2.getValue(1));
            if (collegeCompare != 0) {
                return collegeCompare;
            } else {
                return professionCompare;
            }
        }
    }

    public void insertCollegeName(String collegeName) {
        jdbcTemplate.update("INSERT INTO college_list (college_name) VALUES (?)", collegeName);
    }

    public void insertProfessionData(Triplet<String, String, String> professionDataCurr) {
        String college_name = professionDataCurr.getValue0();
        String profession_name = professionDataCurr.getValue1();
        String profession_domain = professionDataCurr.getValue2();
        Integer college_id = jdbcTemplate.queryForObject("SELECT college_id FROM college_list WHERE college_name = ?", Integer.class, college_name);
        jdbcTemplate.update("INSERT INTO profession_list (college_id, profession_name, profession_domain) VALUES (?, ?, ?)", college_id, profession_name, profession_domain);
    }

}
