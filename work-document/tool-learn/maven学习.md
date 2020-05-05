## How to include/exclude content from jar artifact

Specify a list of fileset patterns to be included or excluded by adding `<includes>`/`<include>` or `<excludes>`/`<exclude>` in your `pom.xml`.

```xml-dtd
<project>
  ...
  <build>
    <plugins>
      ...
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-jar-plugin</artifactId>
        <version>3.1.2</version>
        <configuration>
          <includes>
            <include>**/service/*</include>
          </includes>
        </configuration>
      </plugin>
      ...
    </plugins>
  </build>
  ...
</project>
```

## Skipping Tests

To skip running the tests for a particular project, set the **skipTests** property to **true**.

```xml-dtd
<project>
  [...]
  <build>
    <plugins>
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-surefire-plugin</artifactId>
        <version>3.0.0-M3</version>
        <configuration>
          <skipTests>true</skipTests>
        </configuration>
      </plugin>
    </plugins>
  </build>
  [...]
</project>
```