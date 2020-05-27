package temp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

@ContextConfiguration("classpath:spring-tools.xml")
public class ImportLabList extends AbstractTestNGSpringContextTests {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Test
    public void doImport() {
        File file = new File("_文档/实验室");
        File[] files = file.listFiles();
        List<String> labs = new LinkedList<>();
        for (File currFile : files) {
            String str = currFile.getName();
            str = str.split("\\.")[0];
            labs.add(str);
        }
        labs.sort(new PinyinComparator());
        for (String labName : labs) {
            jdbcTemplate.update("INSERT INTO lab_list (lab_name) VALUE (?)", labName);
        }
    }
}
