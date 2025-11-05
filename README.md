A lightweight Java client for managing a [Garage](https://garage.deuxfleurs.fr/) S3-compatible object storage, with full **Spring Boot auto-configuration support**.

This tool provides a comprehensive administrative API, allowing you to programmatically manage tasks related to buckets, S3 access keys, admin tokens, and server health.

## Key Features

*   **Effortless Spring Boot Integration**: Includes a starter for full auto-configuration. Just add the dependency and properties.
*   **Comprehensive Bucket Management**: Full CRUD (Create, Read, Update, Delete) operations for buckets, plus object inspection and cleanup.
*   **Access Key Administration**: Full lifecycle management for S3 access keys, including creation, import, and updates.
*   **Admin Token Control**: Manage Garage's internal administrative tokens with a dedicated client.
*   **Server Utilities**: Check server health, Prometheus metrics, and perform other special operations.
*   **Safe & Fluent API**: Uses the Builder pattern to create complex requests safely and readably.
*   **Modern Java**: Built with modern Java features, including Records for immutable data models.

---

## Usage with Spring Boot (Recommended)

This is the easiest and recommended way to use the client.

### 1. Add the Maven Dependency

Add the starter dependency to your project's `pom.xml`:

```xml
<dependency>
    <groupId>com.storehub.garage.admin</groupId>
    <artifactId>garage-admin-client-spring-boot-starter</artifactId>
    <version>1.0.0</version> <!-- Or the latest version -->
</dependency>
```

### 2. Configure in `application.yml`

Add the following properties to your `application.yml` file and provide your Garage instance details:

```yaml
garage:
  admin:
    url: http://YOUR_GARAGE_HOST:3903
    master-token: YOUR_SECRET_ADMIN_TOKEN
```

### 3. Inject and Use

That's it! The `GarageAdminClient` is now configured and available in your Spring context. You can inject it directly into any of your components, like a `@Service`.

```java
import com.storehub.garage.admin.GarageAdminClient;
import org.springframework.stereotype.Service;

@Service
public class MyKeyManagementService {

    private final GarageAdminClient garageClient;

    // The client is automatically injected by Spring
    public MyKeyManagementService(GarageAdminClient garageClient) {
        this.garageClient = garageClient;
    }

    public void createNewApplicationKey() {
        CreateKey request = CreateKey.builder("my-app-key")
                .withBucketCreation()
                .build();
        
        ApiKey newKey = garageClient.createKey(request);
        System.out.println("Created Access Key: " + newKey.accessKeyId());
    }
}
```

---

## Standalone Usage (Without Spring Boot)

If you are not using Spring Boot, you can configure and instantiate the client manually.

### 1. Build the Project

Clone the repository and run the Maven build command:
```bash
mvn clean install

### 2. Configure and Instantiate the Client

java
import com.storehub.garage.admin.GarageAdminClient;
import com.storehub.garage.admin.config.GarageAdminClientConfig;

// 1. Create the configuration object
GarageAdminClientConfig config = new GarageAdminClientConfig(
    "http://YOUR_GARAGE_HOST:3903",
    "YOUR_SECRET_ADMIN_TOKEN"
);

// 2. Build the client instance
GarageAdminClient client = new GarageAdminClient.Builder(config).build();

// 3. Use the client
ApiKey key = client.getKey("someAccessKeyId");
```

## License

This project is licensed under the MIT License - see the `LICENSE.md` file for details.