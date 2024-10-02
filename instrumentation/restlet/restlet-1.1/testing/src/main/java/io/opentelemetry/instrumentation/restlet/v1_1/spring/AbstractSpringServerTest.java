/*
 * Copyright The OpenTelemetry Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package io.opentelemetry.instrumentation.restlet.v1_1.spring;

import io.opentelemetry.instrumentation.restlet.v1_1.AbstractRestletServerTest;
import io.opentelemetry.instrumentation.testing.junit.http.HttpServerTestOptions;
import org.restlet.Component;
import org.restlet.Router;
import org.restlet.Server;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public abstract class AbstractSpringServerTest extends AbstractRestletServerTest {

  protected Router router;

  protected abstract String getConfigurationName();

  @Override
  protected void setupServer(Component component) {
    ApplicationContext context = new ClassPathXmlApplicationContext(getConfigurationName());
    router = (Router) context.getBean("testRouter");
    Server server = (Server) context.getBean("testServer", new Object[] {"http", port});
    component.getServers().add(server);
  }

  @Override
  protected void setupRouting() {
    host.attach(router);
  }

  @Override
  protected void configure(HttpServerTestOptions options) {
    super.configure(options);
    options.setResponseCodeOnNonStandardHttpMethod(405);
  }
}