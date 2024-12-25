package ilcarro.utils;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {
    int retryCount = 0;

    private static int maxTryValue = 3;


    @Override
    public boolean retry(ITestResult result) {
        if(retryCount < maxTryValue) {
            retryCount++;
            return true;
        }
        return false;
    }
}
