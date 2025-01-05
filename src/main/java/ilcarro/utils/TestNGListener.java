package ilcarro.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestNGListener implements ITestListener {
    Logger logger = LoggerFactory.getLogger(TestNGListener.class);


    @Override
    public void onTestStart(ITestResult result) {
        ITestListener.super.onTestStart(result);
        //      logger.info("start test --> "+ result.getMethod());
        logger.info("\n\t\t=== START === test -> "
                + result.getName()
                + "  in class: " + result.getTestClass().getName());

    }

    @Override
    public void onTestSuccess(ITestResult result) {
        ITestListener.super.onTestSuccess(result);
        //       logger.info("success test --> "+ result.getMethod());
        logger.info("\n\t\t=== SUCCESS === test -> "
                + result.getName()
                + "  in class: " + result.getTestClass().getName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        ITestListener.super.onTestFailure(result);
        //       logger.info("fail test --> "+ result.getMethod());
        logger.info("\n\t\t=== FAIL === test -> "
                + result.getName()
                + "  in class:" + result.getTestClass().getName());

    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ITestListener.super.onTestSkipped(result);
//        logger.info("skipped test --> "+ result.getMethod());
        logger.info("\n\t\t=== SKIPP === test -> "
                + result.getName()
                + "  in class:" + result.getTestClass().getName());

    }

//    @Override
//    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
//        ITestListener.super.onTestFailedButWithinSuccessPercentage(result);
//    }  // nit use now

//    @Override
//    public void onTestFailedWithTimeout(ITestResult result) {
//        ITestListener.super.onTestFailedWithTimeout(result);
//    }

    @Override
    public void onStart(ITestContext context) {
        ITestListener.super.onStart(context);
        logger.info("start testing on date--> "
                + "\n\t\t============ start ============"
                + context.getStartDate());

    }

    @Override
    public void onFinish(ITestContext context) {
        ITestListener.super.onFinish(context);
        logger.info("stop testing on date--> "
                + "\n\t\t============ stop =============="
                + context.getEndDate());
    }
}
