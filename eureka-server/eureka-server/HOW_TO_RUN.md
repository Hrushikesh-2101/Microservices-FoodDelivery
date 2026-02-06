# How to Run Eureka Server

## Why the "Application" run fails with Jettison error

The error `NoClassDefFoundError: org/codehaus/jettison/mapped/Configuration` happens because **IntelliJ's default "Application" run does not put the `jettison` JAR on the classpath** for this project, even though it is in `pom.xml`. This is an IDE classpath issue, not a Spring Boot or Java version problem.

- **Spring Boot 2.7.18** and **Java 1.8** are compatible.
- The **jettison** dependency is correctly declared in `pom.xml`; Maven includes it when it builds the classpath.

## Reliable way to run (recommended)

### Option A: Use the "Eureka Server (Maven)" run configuration (root project)

If you opened the **Microservices-FoodDelivery** root folder in IntelliJ:

1. In the run configurations dropdown (top right), select **"Eureka Server (Maven)"**.
2. Click **Run**. The app will start with Maven’s classpath (including jettison).

### Option B: Run from terminal

From this directory (`eureka-server/eureka-server`):

```bat
mvnw.cmd spring-boot:run
```

Or, if you use a global Maven install:

```bat
mvn spring-boot:run
```

### Option C: Fix the default "Application" run in IntelliJ

1. **Reload Maven:** Right-click `pom.xml` → **Maven** → **Reload project** (or use the Maven tool window refresh).
2. **Invalidate caches:** **File** → **Invalidate Caches…** → **Invalidate and Restart**.
3. **Recreate the run config:** **Run** → **Edit Configurations** → remove the existing "EurekaServerApplication" config → click **OK**. Then run `EurekaServerApplication` again from the editor (green play) so IntelliJ creates a new run configuration with the updated classpath.

After that, the default Application run may include jettison; if not, keep using Option A or B.
