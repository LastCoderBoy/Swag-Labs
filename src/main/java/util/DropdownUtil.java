package util;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public final class DropdownUtil {

    private DropdownUtil() {
        throw new UnsupportedOperationException("DropdownUtil class cannot be instantiated");
    }

    public static void selectByValue(WebElement element, String value){
        getSelect(element).selectByValue(value);
    }

    public static void selectByIndex(WebElement element, int index){
        getSelect(element).selectByIndex(index);
    }

    public static void selectByVisibleText(WebElement element, String text){
        getSelect(element).selectByVisibleText(text);
    }


    // ================= Private Methods =================
    private static Select getSelect(WebElement element){
        return new Select(element);
    }

}
