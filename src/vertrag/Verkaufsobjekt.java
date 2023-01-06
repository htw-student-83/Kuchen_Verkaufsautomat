package vertrag;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;

public interface Verkaufsobjekt {
    BigDecimal getPreis();
    Date getInspektionsdatum() throws ParseException;
    int getFachnummer();
}
