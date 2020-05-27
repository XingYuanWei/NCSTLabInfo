package temp;

import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;

class PinyinComparator implements Comparator<String> {
    @Override
    public int compare(String o1, String o2) {
        Collator collator = Collator.getInstance(Locale.CHINESE);
        return collator.compare(o1, o2);
    }
}
