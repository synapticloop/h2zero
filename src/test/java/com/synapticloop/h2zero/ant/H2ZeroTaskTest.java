package com.synapticloop.h2zero.ant;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.synapticloop.h2zero.plugin.ant.H2ZeroTask;

public class H2ZeroTaskTest {
	private H2ZeroTask h2ZeroTask;

	@Mock private Project mockProject;

	@Before
	public void before() {
		MockitoAnnotations.openMocks(this);
	}

	@Before
	public void setup() {
		h2ZeroTask = new H2ZeroTask();
	}

	@Test(expected = BuildException.class)
	public void testIncorrectFiles() {
		h2ZeroTask.setProject(mockProject);
		h2ZeroTask.setVerbose(false);
		h2ZeroTask.setInFile("something");
		h2ZeroTask.setOutDir("somethingElse");
		h2ZeroTask.execute();
	}
}
