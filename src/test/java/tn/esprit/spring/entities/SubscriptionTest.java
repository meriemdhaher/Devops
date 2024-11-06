package tn.esprit.spring.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.spring.repositories.ISkierRepository;
import tn.esprit.spring.repositories.ISubscriptionRepository;
import tn.esprit.spring.services.SubscriptionServicesImpl;
import static org.mockito.Mockito.*;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class SubscriptionTest {

    @Mock
    private ISubscriptionRepository subscriptionRepository;

    @Mock
    private ISkierRepository skierRepository;

    @InjectMocks
    private SubscriptionServicesImpl subscriptionServices;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddSubscription() {
        // Arrange
        Subscription subscription = new Subscription();
        subscription.setTypeSub(TypeSubscription.MONTHLY);
        subscription.setStartDate(LocalDate.now());

        Subscription savedSubscription = new Subscription();
        savedSubscription.setEndDate(subscription.getStartDate().plusMonths(1));

        when(subscriptionRepository.save(subscription)).thenReturn(savedSubscription);

        // Act
        Subscription result = subscriptionServices.addSubscription(subscription);

        // Assert
        assertNotNull(result);
        assertEquals(subscription.getStartDate().plusMonths(1), result.getEndDate());
        verify(subscriptionRepository, times(1)).save(subscription);
    }

    @Test
    void testUpdateSubscription() {
        // Arrange
        Subscription subscription = new Subscription();
        subscription.setNumSub(1L);
        when(subscriptionRepository.save(subscription)).thenReturn(subscription);

        // Act
        Subscription result = subscriptionServices.updateSubscription(subscription);

        // Assert
        assertNotNull(result);
        assertEquals(subscription.getNumSub(), result.getNumSub());
        verify(subscriptionRepository, times(1)).save(subscription);
    }

    @Test
    void testRetrieveSubscriptionById() {
        // Arrange
        Long subscriptionId = 1L;
        Subscription subscription = new Subscription();
        subscription.setNumSub(subscriptionId);

        when(subscriptionRepository.findById(subscriptionId)).thenReturn(Optional.of(subscription));

        // Act
        Subscription result = subscriptionServices.retrieveSubscriptionById(subscriptionId);

        // Assert
        assertNotNull(result);
        assertEquals(subscriptionId, result.getNumSub());
        verify(subscriptionRepository, times(1)).findById(subscriptionId);
    }

    @Test
    void testGetSubscriptionByType() {

    }

    @Test
    void testRetrieveSubscriptionsByDates() {
        // Arrange
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusMonths(1);
        List<Subscription> subscriptions = List.of(new Subscription());

        when(subscriptionRepository.getSubscriptionsByStartDateBetween(startDate, endDate)).thenReturn(subscriptions);

        // Act
        List<Subscription> result = subscriptionServices.retrieveSubscriptionsByDates(startDate, endDate);

        // Assert
        assertEquals(subscriptions, result);
        verify(subscriptionRepository, times(1)).getSubscriptionsByStartDateBetween(startDate, endDate);
    }

    @Test
    void testRetrieveSubscriptions() {
        // Arrange
        Subscription subscription = new Subscription();
        subscription.setNumSub(1L);
        subscription.setEndDate(LocalDate.now());
        Skier skier = new Skier();
        skier.setFirstName("John");
        skier.setLastName("Doe");

        when(subscriptionRepository.findDistinctOrderByEndDateAsc()).thenReturn(List.of(subscription));
        when(skierRepository.findBySubscription(subscription)).thenReturn(skier);

        // Act
        subscriptionServices.retrieveSubscriptions();

        // Verify
        verify(subscriptionRepository, times(1)).findDistinctOrderByEndDateAsc();
        verify(skierRepository, times(1)).findBySubscription(subscription);
    }

    @Test
    void testShowMonthlyRecurringRevenue() {
        // Arrange
        when(subscriptionRepository.recurringRevenueByTypeSubEquals(TypeSubscription.MONTHLY)).thenReturn(100.0f);
        when(subscriptionRepository.recurringRevenueByTypeSubEquals(TypeSubscription.SEMESTRIEL)).thenReturn(600.0f); // divided by 6
        when(subscriptionRepository.recurringRevenueByTypeSubEquals(TypeSubscription.ANNUAL)).thenReturn(1200.0f);    // divided by 12

        // Act
        subscriptionServices.showMonthlyRecurringRevenue();

        // Verify that each revenue method is called once
        verify(subscriptionRepository, times(1)).recurringRevenueByTypeSubEquals(TypeSubscription.MONTHLY);
        verify(subscriptionRepository, times(1)).recurringRevenueByTypeSubEquals(TypeSubscription.SEMESTRIEL);
        verify(subscriptionRepository, times(1)).recurringRevenueByTypeSubEquals(TypeSubscription.ANNUAL);
    }
}