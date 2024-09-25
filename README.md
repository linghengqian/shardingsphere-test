# ShardingSphere-Test

- For https://github.com/apache/shardingsphere/issues/32765 .

- Verified unit test under `Ubuntu 22.04.4 LTS` with `Docker Engine` and `SDKMAN!`.

```shell
sdk install java 8.0.422-tem
sdk install java 22.0.2-graalce
git clone git@github.com:apache/shardingsphere.git
cd ./shardingsphere/
sdk use java 22.0.2-graalce
./mvnw clean install -Prelease -T1C -DskipTests
cd ../

git clone git@github.com:linghengqian/shardingsphere-test.git -b fail-test
cd ./shardingsphere-test/
sdk use java 8.0.422-tem
./mvnw -T 1C clean test
```

- The unit tests execute fine.

```shell
org.opentest4j.AssertionFailedError: Unexpected exception thrown: org.springframework.jdbc.UncategorizedSQLException: StatementCallback; uncategorized SQLException for SQL [select '']; SQL state [null]; error code [0]; connection disabled; nested exception is java.sql.SQLException: connection disabled

	at org.junit.jupiter.api.AssertDoesNotThrow.createAssertionFailedError(AssertDoesNotThrow.java:83)
	at org.junit.jupiter.api.AssertDoesNotThrow.assertDoesNotThrow(AssertDoesNotThrow.java:54)
	at org.junit.jupiter.api.AssertDoesNotThrow.assertDoesNotThrow(AssertDoesNotThrow.java:37)
	at org.junit.jupiter.api.Assertions.assertDoesNotThrow(Assertions.java:3135)
	at com.hulalaga.ShardingSphereTest.lambda$testDruidConnectionDisabled$2(ShardingSphereTest.java:65)
	at java.base/java.util.stream.Streams$RangeIntSpliterator.forEachRemaining(Streams.java:104)
	at java.base/java.util.stream.IntPipeline$Head.forEachOrdered(IntPipeline.java:630)
	at com.hulalaga.ShardingSphereTest.testDruidConnectionDisabled(ShardingSphereTest.java:64)
	at java.base/jdk.internal.reflect.DirectMethodHandleAccessor.invoke(DirectMethodHandleAccessor.java:103)
	at java.base/java.lang.reflect.Method.invoke(Method.java:580)
	at org.junit.platform.commons.util.ReflectionUtils.invokeMethod(ReflectionUtils.java:725)
	at org.junit.jupiter.engine.execution.MethodInvocation.proceed(MethodInvocation.java:60)
	at org.junit.jupiter.engine.execution.InvocationInterceptorChain$ValidatingInvocation.proceed(InvocationInterceptorChain.java:131)
	at org.junit.jupiter.engine.extension.TimeoutExtension.intercept(TimeoutExtension.java:149)
	at org.junit.jupiter.engine.extension.TimeoutExtension.interceptTestableMethod(TimeoutExtension.java:140)
	at org.junit.jupiter.engine.extension.TimeoutExtension.interceptTestMethod(TimeoutExtension.java:84)
	at org.junit.jupiter.engine.execution.ExecutableInvoker$ReflectiveInterceptorCall.lambda$ofVoidMethod$0(ExecutableInvoker.java:115)
	at org.junit.jupiter.engine.execution.ExecutableInvoker.lambda$invoke$0(ExecutableInvoker.java:105)
	at org.junit.jupiter.engine.execution.InvocationInterceptorChain$InterceptedInvocation.proceed(InvocationInterceptorChain.java:106)
	at org.junit.jupiter.engine.execution.InvocationInterceptorChain.proceed(InvocationInterceptorChain.java:64)
	at org.junit.jupiter.engine.execution.InvocationInterceptorChain.chainAndInvoke(InvocationInterceptorChain.java:45)
	at org.junit.jupiter.engine.execution.InvocationInterceptorChain.invoke(InvocationInterceptorChain.java:37)
	at org.junit.jupiter.engine.execution.ExecutableInvoker.invoke(ExecutableInvoker.java:104)
	at org.junit.jupiter.engine.execution.ExecutableInvoker.invoke(ExecutableInvoker.java:98)
	at org.junit.jupiter.engine.descriptor.TestMethodTestDescriptor.lambda$invokeTestMethod$7(TestMethodTestDescriptor.java:214)
	at org.junit.platform.engine.support.hierarchical.ThrowableCollector.execute(ThrowableCollector.java:73)
	at org.junit.jupiter.engine.descriptor.TestMethodTestDescriptor.invokeTestMethod(TestMethodTestDescriptor.java:210)
	at org.junit.jupiter.engine.descriptor.TestMethodTestDescriptor.execute(TestMethodTestDescriptor.java:135)
	at org.junit.jupiter.engine.descriptor.TestMethodTestDescriptor.execute(TestMethodTestDescriptor.java:66)
	at org.junit.platform.engine.support.hierarchical.NodeTestTask.lambda$executeRecursively$6(NodeTestTask.java:151)
	at org.junit.platform.engine.support.hierarchical.ThrowableCollector.execute(ThrowableCollector.java:73)
	at org.junit.platform.engine.support.hierarchical.NodeTestTask.lambda$executeRecursively$8(NodeTestTask.java:141)
	at org.junit.platform.engine.support.hierarchical.Node.around(Node.java:137)
	at org.junit.platform.engine.support.hierarchical.NodeTestTask.lambda$executeRecursively$9(NodeTestTask.java:139)
	at org.junit.platform.engine.support.hierarchical.ThrowableCollector.execute(ThrowableCollector.java:73)
	at org.junit.platform.engine.support.hierarchical.NodeTestTask.executeRecursively(NodeTestTask.java:138)
	at org.junit.platform.engine.support.hierarchical.NodeTestTask.execute(NodeTestTask.java:95)
	at java.base/java.util.ArrayList.forEach(ArrayList.java:1597)
	at org.junit.platform.engine.support.hierarchical.SameThreadHierarchicalTestExecutorService.invokeAll(SameThreadHierarchicalTestExecutorService.java:41)
	at org.junit.platform.engine.support.hierarchical.NodeTestTask.lambda$executeRecursively$6(NodeTestTask.java:155)
	at org.junit.platform.engine.support.hierarchical.ThrowableCollector.execute(ThrowableCollector.java:73)
	at org.junit.platform.engine.support.hierarchical.NodeTestTask.lambda$executeRecursively$8(NodeTestTask.java:141)
	at org.junit.platform.engine.support.hierarchical.Node.around(Node.java:137)
	at org.junit.platform.engine.support.hierarchical.NodeTestTask.lambda$executeRecursively$9(NodeTestTask.java:139)
	at org.junit.platform.engine.support.hierarchical.ThrowableCollector.execute(ThrowableCollector.java:73)
	at org.junit.platform.engine.support.hierarchical.NodeTestTask.executeRecursively(NodeTestTask.java:138)
	at org.junit.platform.engine.support.hierarchical.NodeTestTask.execute(NodeTestTask.java:95)
	at java.base/java.util.ArrayList.forEach(ArrayList.java:1597)
	at org.junit.platform.engine.support.hierarchical.SameThreadHierarchicalTestExecutorService.invokeAll(SameThreadHierarchicalTestExecutorService.java:41)
	at org.junit.platform.engine.support.hierarchical.NodeTestTask.lambda$executeRecursively$6(NodeTestTask.java:155)
	at org.junit.platform.engine.support.hierarchical.ThrowableCollector.execute(ThrowableCollector.java:73)
	at org.junit.platform.engine.support.hierarchical.NodeTestTask.lambda$executeRecursively$8(NodeTestTask.java:141)
	at org.junit.platform.engine.support.hierarchical.Node.around(Node.java:137)
	at org.junit.platform.engine.support.hierarchical.NodeTestTask.lambda$executeRecursively$9(NodeTestTask.java:139)
	at org.junit.platform.engine.support.hierarchical.ThrowableCollector.execute(ThrowableCollector.java:73)
	at org.junit.platform.engine.support.hierarchical.NodeTestTask.executeRecursively(NodeTestTask.java:138)
	at org.junit.platform.engine.support.hierarchical.NodeTestTask.execute(NodeTestTask.java:95)
	at org.junit.platform.engine.support.hierarchical.SameThreadHierarchicalTestExecutorService.submit(SameThreadHierarchicalTestExecutorService.java:35)
	at org.junit.platform.engine.support.hierarchical.HierarchicalTestExecutor.execute(HierarchicalTestExecutor.java:57)
	at org.junit.platform.engine.support.hierarchical.HierarchicalTestEngine.execute(HierarchicalTestEngine.java:54)
	at org.junit.platform.launcher.core.EngineExecutionOrchestrator.execute(EngineExecutionOrchestrator.java:107)
	at org.junit.platform.launcher.core.EngineExecutionOrchestrator.execute(EngineExecutionOrchestrator.java:88)
	at org.junit.platform.launcher.core.EngineExecutionOrchestrator.lambda$execute$0(EngineExecutionOrchestrator.java:54)
	at org.junit.platform.launcher.core.EngineExecutionOrchestrator.withInterceptedStreams(EngineExecutionOrchestrator.java:67)
	at org.junit.platform.launcher.core.EngineExecutionOrchestrator.execute(EngineExecutionOrchestrator.java:52)
	at org.junit.platform.launcher.core.DefaultLauncher.execute(DefaultLauncher.java:114)
	at org.junit.platform.launcher.core.DefaultLauncher.execute(DefaultLauncher.java:86)
	at org.junit.platform.launcher.core.DefaultLauncherSession$DelegatingLauncher.execute(DefaultLauncherSession.java:86)
	at org.junit.platform.launcher.core.SessionPerRequestLauncher.execute(SessionPerRequestLauncher.java:53)
	at com.intellij.junit5.JUnit5IdeaTestRunner.startRunnerWithArgs(JUnit5IdeaTestRunner.java:57)
	at com.intellij.rt.junit.IdeaTestRunner$Repeater$1.execute(IdeaTestRunner.java:38)
	at com.intellij.rt.execution.junit.TestsRepeater.repeat(TestsRepeater.java:11)
	at com.intellij.rt.junit.IdeaTestRunner$Repeater.startRunnerWithArgs(IdeaTestRunner.java:35)
	at com.intellij.rt.junit.JUnitStarter.prepareStreamsAndStart(JUnitStarter.java:232)
	at com.intellij.rt.junit.JUnitStarter.main(JUnitStarter.java:55)
Caused by: org.springframework.jdbc.UncategorizedSQLException: StatementCallback; uncategorized SQLException for SQL [select '']; SQL state [null]; error code [0]; connection disabled; nested exception is java.sql.SQLException: connection disabled
	at org.springframework.jdbc.core.JdbcTemplate.translateException(JdbcTemplate.java:1542)
	at org.springframework.jdbc.core.JdbcTemplate.execute(JdbcTemplate.java:393)
	at org.springframework.jdbc.core.JdbcTemplate.execute(JdbcTemplate.java:431)
	at com.hulalaga.ShardingSphereTest.lambda$testDruidConnectionDisabled$0(ShardingSphereTest.java:65)
	at org.junit.jupiter.api.AssertDoesNotThrow.assertDoesNotThrow(AssertDoesNotThrow.java:50)
	... 73 more
Caused by: java.sql.SQLException: connection disabled
	at com.alibaba.druid.pool.DruidPooledConnection.checkStateInternal(DruidPooledConnection.java:1184)
	at com.alibaba.druid.pool.DruidPooledConnection.checkState(DruidPooledConnection.java:1169)
	at com.alibaba.druid.pool.DruidPooledConnection.createStatement(DruidPooledConnection.java:676)
	at org.apache.shardingsphere.driver.jdbc.core.statement.StatementManager.createStatement(StatementManager.java:73)
	at org.apache.shardingsphere.driver.jdbc.core.statement.StatementManager.createStorageResource(StatementManager.java:48)
	at org.apache.shardingsphere.driver.jdbc.core.statement.StatementManager.createStorageResource(StatementManager.java:40)
	at org.apache.shardingsphere.infra.executor.sql.prepare.driver.jdbc.builder.StatementExecutionUnitBuilder.createStatement(StatementExecutionUnitBuilder.java:45)
	at org.apache.shardingsphere.infra.executor.sql.prepare.driver.jdbc.builder.StatementExecutionUnitBuilder.build(StatementExecutionUnitBuilder.java:40)
	at org.apache.shardingsphere.infra.executor.sql.prepare.driver.jdbc.builder.StatementExecutionUnitBuilder.build(StatementExecutionUnitBuilder.java:35)
	at org.apache.shardingsphere.infra.executor.sql.prepare.driver.DriverExecutionPrepareEngine.createExecutionGroup(DriverExecutionPrepareEngine.java:102)
	at org.apache.shardingsphere.infra.executor.sql.prepare.driver.DriverExecutionPrepareEngine.group(DriverExecutionPrepareEngine.java:92)
	at org.apache.shardingsphere.infra.executor.sql.prepare.AbstractExecutionPrepareEngine.prepare(AbstractExecutionPrepareEngine.java:70)
	at org.apache.shardingsphere.infra.executor.sql.prepare.AbstractExecutionPrepareEngine.prepare(AbstractExecutionPrepareEngine.java:59)
	at org.apache.shardingsphere.driver.jdbc.core.statement.ShardingSphereStatement.createExecutionGroupContext(ShardingSphereStatement.java:528)
	at org.apache.shardingsphere.driver.jdbc.core.statement.ShardingSphereStatement.useDriverToExecute(ShardingSphereStatement.java:562)
	at org.apache.shardingsphere.driver.jdbc.core.statement.ShardingSphereStatement.executeWithExecutionContext(ShardingSphereStatement.java:541)
	at org.apache.shardingsphere.driver.jdbc.core.statement.ShardingSphereStatement.execute0(ShardingSphereStatement.java:466)
	at org.apache.shardingsphere.driver.jdbc.core.statement.ShardingSphereStatement.execute(ShardingSphereStatement.java:394)
	at com.zaxxer.hikari.pool.ProxyStatement.execute(ProxyStatement.java:94)
	at com.zaxxer.hikari.pool.HikariProxyStatement.execute(HikariProxyStatement.java)
	at org.springframework.jdbc.core.JdbcTemplate$1ExecuteStatementCallback.doInStatement(JdbcTemplate.java:422)
	at org.springframework.jdbc.core.JdbcTemplate.execute(JdbcTemplate.java:381)
	... 76 more
Caused by: java.sql.SQLSyntaxErrorException: INSERT command denied to user 'readonly'@'172.17.0.1' for table 'the_table'
	at com.mysql.cj.jdbc.exceptions.SQLError.createSQLException(SQLError.java:120)
	at com.mysql.cj.jdbc.exceptions.SQLExceptionsMapping.translateException(SQLExceptionsMapping.java:122)
	at com.mysql.cj.jdbc.StatementImpl.executeInternal(StatementImpl.java:763)
	at com.mysql.cj.jdbc.StatementImpl.execute(StatementImpl.java:648)
	at com.alibaba.druid.pool.DruidPooledStatement.execute(DruidPooledStatement.java:635)
	at org.apache.shardingsphere.driver.jdbc.core.statement.ShardingSphereStatement.lambda$execute$8(ShardingSphereStatement.java:394)
	at org.apache.shardingsphere.driver.jdbc.core.statement.ShardingSphereStatement$2.executeSQL(ShardingSphereStatement.java:583)
	at org.apache.shardingsphere.driver.jdbc.core.statement.ShardingSphereStatement$2.executeSQL(ShardingSphereStatement.java:579)
	at org.apache.shardingsphere.infra.executor.sql.execute.engine.driver.jdbc.JDBCExecutorCallback.execute(JDBCExecutorCallback.java:85)
	at org.apache.shardingsphere.infra.executor.sql.execute.engine.driver.jdbc.JDBCExecutorCallback.execute(JDBCExecutorCallback.java:64)
	at org.apache.shardingsphere.infra.executor.kernel.ExecutorEngine.syncExecute(ExecutorEngine.java:99)
	at org.apache.shardingsphere.infra.executor.kernel.ExecutorEngine.parallelExecute(ExecutorEngine.java:95)
	at org.apache.shardingsphere.infra.executor.kernel.ExecutorEngine.execute(ExecutorEngine.java:78)
	at org.apache.shardingsphere.infra.executor.sql.execute.engine.driver.jdbc.JDBCExecutor.execute(JDBCExecutor.java:66)
	at org.apache.shardingsphere.infra.executor.sql.execute.engine.driver.jdbc.JDBCExecutor.execute(JDBCExecutor.java:50)
	at org.apache.shardingsphere.driver.executor.DriverJDBCExecutor.doExecute(DriverJDBCExecutor.java:148)
	at org.apache.shardingsphere.driver.executor.DriverJDBCExecutor.execute(DriverJDBCExecutor.java:139)
	at org.apache.shardingsphere.driver.jdbc.core.statement.ShardingSphereStatement.useDriverToExecute(ShardingSphereStatement.java:565)
	at org.apache.shardingsphere.driver.jdbc.core.statement.ShardingSphereStatement.executeWithExecutionContext(ShardingSphereStatement.java:541)
	at org.apache.shardingsphere.driver.jdbc.core.statement.ShardingSphereStatement.execute0(ShardingSphereStatement.java:466)
	at org.apache.shardingsphere.driver.jdbc.core.statement.ShardingSphereStatement.execute(ShardingSphereStatement.java:394)
	at com.zaxxer.hikari.pool.ProxyStatement.execute(ProxyStatement.java:94)
	at com.zaxxer.hikari.pool.HikariProxyStatement.execute(HikariProxyStatement.java)
	at org.springframework.jdbc.core.JdbcTemplate$1ExecuteStatementCallback.doInStatement(JdbcTemplate.java:422)
	at org.springframework.jdbc.core.JdbcTemplate.execute(JdbcTemplate.java:381)
	at org.springframework.jdbc.core.JdbcTemplate.execute(JdbcTemplate.java:431)
	at com.hulalaga.ShardingSphereTest.lambda$testDruidConnectionDisabled$1(ShardingSphereTest.java:66)
	at org.junit.jupiter.api.AssertThrows.assertThrows(AssertThrows.java:55)
	at org.junit.jupiter.api.AssertThrows.assertThrows(AssertThrows.java:37)
	at org.junit.jupiter.api.Assertions.assertThrows(Assertions.java:3082)
	at com.hulalaga.ShardingSphereTest.lambda$testDruidConnectionDisabled$2(ShardingSphereTest.java:66)
	... 70 more
```
