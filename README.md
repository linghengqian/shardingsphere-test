# ShardingSphere-Test

- For https://github.com/apache/shardingsphere/issues/32765 .

- Verified unit test under `Ubuntu 22.04.4 LTS` with `Docker Engine` and `SDKMAN!`.

```shell
sdk install java 8.0.422-tem
git clone git@github.com:linghengqian/shardingsphere-test.git
cd ./shardingsphere-test/
sdk use java 8.0.422-tem
./mvnw -T 1C clean test 
```

- The unit tests execute fine.

```shell
[INFO] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 13.55 s -- in com.hulalaga.ShardingSphereTest
[INFO] 
[INFO] Results:
[INFO] 
[INFO] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0
[INFO] 
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  16.236 s (Wall Clock)
[INFO] Finished at: 2024-09-25T12:25:36+08:00
[INFO] ------------------------------------------------------------------------
```
