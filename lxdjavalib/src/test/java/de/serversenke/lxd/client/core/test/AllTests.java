package de.serversenke.lxd.client.core.test;

import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
@SelectPackages({"de.serversenke.lxd.client.core.test.api", "de.serversenke.lxd.client.core.test.model"})
public class AllTests
{
}
