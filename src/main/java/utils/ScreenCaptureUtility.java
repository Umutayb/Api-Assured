package utils;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.RemoteWebDriver;
import static resources.Colors.*;

import java.io.File;

public class ScreenCaptureUtility {

    Printer log = new Printer(ScreenCaptureUtility.class);
    NumericUtilities numeric = new NumericUtilities();

    public File captureScreen(String specName, RemoteWebDriver driver) {
        try {
            log.new Info("Capturing page");

            String name = specName+"#"+numeric.randomNumber(1,10000)+".jpg";
            File sourceFile = new File("Screenshots");
            File fileDestination  = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(fileDestination, new File(sourceFile, name));

            log.new Info("Screenshot saved as; "+name+" at the \"Screenshots\" file.");
            return fileDestination;
        }
        catch (Exception gamma){
            Assert.fail(YELLOW+"Could not capture screen"+RED+"\n\t"+gamma+RESET);
            driver.quit();
            return null;
        }
    }
}
