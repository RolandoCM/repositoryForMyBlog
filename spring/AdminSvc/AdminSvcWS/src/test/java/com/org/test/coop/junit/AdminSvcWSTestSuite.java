package com.org.test.coop.junit;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.org.test.coop.master.junit.BranchWSTest;
import com.org.test.coop.master.junit.CountryStateDistTest;
import com.org.test.coop.master.junit.ModuleWSTest;
import com.org.test.coop.master.junit.SecurityQuestionTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	ModuleWSTest.class,
	SecurityQuestionTest.class,
    CountryStateDistTest.class,
	BranchWSTest.class
})
public class AdminSvcWSTestSuite {
	
}