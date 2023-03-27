package driver;

import com.thoughtworks.gauge.AfterStep;
import com.thoughtworks.gauge.ExecutionContext;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.inject.Inject;
import javax.print.attribute.standard.Media;
import java.io.File;
import java.sql.DriverAction;

public class ScreenshotImpl {

    private final Logger logger = LoggerFactory.getLogger(ScreenshotImpl.class);

    @Inject
    DriverAction driverAction;

    public ScreenshotImpl() {

    }

    @AfterStep
    public void afterStep(ExecutionContext executionContext) {
        try {
            logger.debug("After step");
            if (executionContext.getCurrentScenario().getIsFailing()) {
                logger.info(executionContext.getCurrentStep().getDynamicText() + "step failed");
                logger.error(executionContext.getCurrentStep().getStackTrace());
                Media media = null;
                File screenshotFile = ((TakesScreenshot) driverAction).getScreenshotAs(OutputType.FILE);
                FileUtils.copyFile(screenshotFile, new File("out\\Screenshot.png"));
            }

        } catch (Exception e) {
            logger.error("After step error", e);
        }
    }

}
