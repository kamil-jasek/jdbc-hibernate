package pl.sda.hibernate;

import java.io.IOException;
import java.util.Properties;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import pl.sda.hibernate.entity.Item;
import pl.sda.hibernate.entity.Order;

public final class HibernateUtil {

    private static final SessionFactory factory;

    static {
        try {
            Configuration configuration = new Configuration();
            configuration.setProperties(loadHibernateProperties());
            registerEntities(configuration);

            StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties())
                .build();

            factory = configuration.buildSessionFactory(registry);
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new IllegalStateException(ex.getMessage(), ex);
        }
    }

    private static void registerEntities(Configuration configuration) {
        configuration.addAnnotatedClass(Order.class);
        configuration.addAnnotatedClass(Item.class);
    }

    private static Properties loadHibernateProperties() throws IOException {
        Properties properties = new Properties();
        properties.load(HibernateUtil.class.getClassLoader()
            .getResourceAsStream("hibernate.properties"));
        return properties;
    }

    public static SessionFactory getFactory() {
        return factory;
    }
}
