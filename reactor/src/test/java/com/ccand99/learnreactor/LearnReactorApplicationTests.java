package com.ccand99.learnreactor;

import com.ccand99.learnreactor.observerExample.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LearnReactorApplicationTests {

	@Test
	void contextLoads() {
		System.out.println();
	}

	@Test
	public void observersHandleEventsFromSubject(){
		Subject<String> subject = new ConcreteSubject();
		// 不需要取消订阅时可以用匿名内部类
		Observer<String> observerA = Mockito.spy(new ConcreteObserverA());
		Observer<String> observerB = Mockito.spy(new ConcreteObserverB());

		subject.notifyObservers("No listeners");

		subject.registerObserver(observerA);
		subject.notifyObservers("Message for A");

		subject.registerObserver(observerB);
		subject.notifyObservers("Message for A & B");

		subject.unregisterObserver(observerA);
		subject.notifyObservers("Message for B");

		subject.unregisterObserver(observerB);
		subject.notifyObservers("No listeners");

		//then
		Mockito.verify(observerA,Mockito.times(1)).observe("Message for A");
		Mockito.verify(observerA,Mockito.times(1)).observe("Message for A & B");
		Mockito.verifyNoMoreInteractions(observerA);
		Mockito.verify(observerB,Mockito.times(1)).observe("Message for A & B");
		Mockito.verify(observerB,Mockito.times(1)).observe("Message for B");
		Mockito.verifyNoMoreInteractions(observerB);
	}

}
