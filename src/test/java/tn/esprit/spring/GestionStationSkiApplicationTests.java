package tn.esprit.spring;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import tn.esprit.spring.repositories.ISubscriptionRepository;
import tn.esprit.spring.services.SubscriptionServicesImpl;


@SpringBootTest(classes = SubscriptionServicesImpl.class)
class GestionStationSkiApplicationTests {
	@MockBean
	private SubscriptionServicesImpl subscriptionServices;
	@Test
	void contextLoads() {
	}

}
