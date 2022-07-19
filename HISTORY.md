# Challenge-eventstore Implementation History

## Considerations

- The entire implementation is contained in *EventStoreImplementation.java*, since in the java documentation, a class can implement more than one interface.

- I used synchronized methods as a simple strategy to avoid thread interference and memory consistency errors.

---

## 0.1.0:

- Creating a linkedList **events** as the implementation's data structure. 

    I chose LinkedLists because they are efficient for inserting and deleting data and have a constant complexity of O(1).

- Implementation of the **insert(Event event event)** method of the *EventStore.java* interface. 

    Feature: insert a new event into **events**.

    Rule: I add in **events** the event that is being passed as a parameter in **insert(event)**.

- Implementation of the **removeSpecialCharacters()** method.

    Feature: remove special characters from the **String Type** attribute belonging to the event.

    Rule: from the ASCII table, I use a **.replaceAll** to remove the special characters.

- Implementation of the **removeAll(String type)** method of the *EventStore.java* interface.

    Feature: remove from **events** all events that have the same **Type** attribute. For this method, I used the **getType()** method from the *Events.java* class.

    Rule: I considered that there is no difference between types with the characteristics: uppercase and lowercase, space and special characters. Example: "intelie" = "in telie" = "intélie" = "inté lie".

---

## 0.2.0:

- Implementation of the **moveNext()** method of the *EventIterator.java* interface.

    Feature: return true if there is a next element in **events** or false if there is no next element.
    
    Rules:

    1. when calling the **moveNext()** method, I assign the current size of **events** to a variable called **currentSize()** and assign the value of -1 to a variable called **currentIndex**. This way, when checking for the next element in **events**, the algorithm starts from index [0], the first element;

    2. On each iteration, I add 1 to the variable **currentIndex** until the return from the **moveNext()** method is false.

- Implementation of the **current()** method of the *EventIterator.java* interface.

    Feature: return the current event using the iterator.

    Rule: collect from **events** the event with the current index, using the variable **currentIndex** that is being iterated in **moveNext()**.

- Implementation of the **remove()** method of the *EventIterator.java* interface.

    Feature: remove the current event using the iterator.

    Rule: remove from **events** the event with the current index, using the variable **currentIndex** that is being iterated in **moveNext()**.

- Implementation of the **close()** method of the *EventIterator.java* interface.

    Feature: close all created events.

    Rule: to not overload memory, I close all events by emptying **events**. 

---

## 0.3.0:

- Implementation of **EventIterator** from the *EventStore.java* interface.

    Feature: return all events with the same **String Type** attribute and that their **Long timeStamp** attribute is within the time range {endTime(exclusive) - startTime(inclusive)}.

    Rules: 

    1. At each iteration, I test if there is a next element in **events** using the **moveNext()** method;

    2. I create an instance of the event type called **e** and assign it the current event, using the **current()** method;

    3. I test that the event is contained in the query parameters, satisfying the feature;

    4. If it passes the test, I add **e** to a new globally scoped list called **filteredEvents**;

    5. Within the iteration, I call the **query** so that the iterator passes to the next item of **events** recursively;

    6. After iterating through the entire chained list of **events**, I return a new object of the class **EventStoreImplementation**.

---

## 0.4.0:

- Implementation of the insertion test in *EventTest.java*.

    Feature: test if all events are being inserted correctly.

    Rule: 

    1. I create a new object of class **EventStoreImplementation**;
    
    2. add to **events** 8 new events;

    3. go through the list and test if its current size is 8;

    4. clean up the list using the **close()** method.

- Implementation of the remove from a type test in *EventTest.java*.

    Feature: test if all events of a certain type are being removed correctly.

    Rule: 

    1. I create a new object of the **EventStoreImplementation** class;
    
    2. add to **events** 8 new events with type variations to test all the business rules implemented in **removeAll()**;

    3. call the method **removeAll()** with the type to be searched as parameter

    4. go through the list and test if its current size is 4, because there were 4 elements with the wanted type that should have been removed;

    5. clean up the list using the **close()** method.

- Implementation of the iterator test in *EventTest.java*.

    Feature: test if all events of a certain type are being removed correctly.

    Rule: 

    1. I create a new object of the **EventStoreImplementation** class;
    
    2. add to **events** 8 new events with type variations to test all the business rules implemented in **removeAll()**;

    3. create an EventIterator called iterator, in the new class object created in rule 1, passing in the query parameters that return only 3 events from the list;

    4. I test if its current size is e, since there were 5 elements with type e within the searched time interval that should have been added to the **filteredEvents**;

    5. clean up the list using the **close()** method.