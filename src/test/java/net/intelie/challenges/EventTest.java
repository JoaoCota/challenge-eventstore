package net.intelie.challenges;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class EventTest {
    @Test
    public void thisIsAWarning() throws Exception {
        Event event = new Event("some_type", 123L);

        //THIS IS A WARNING:
        //Some of us (not everyone) are coverage freaks.
        assertEquals(123L, event.getTimestamp());
        assertEquals("some_type", event.getType());
    }

    @Test
    public void insertEvents() throws Exception{
        EventStoreImplementation eventStore = new EventStoreImplementation();
        eventStore.insert(new Event("joAo", 1L));
        eventStore.insert(new Event("João", 2L));
        eventStore.insert(new Event("joaO", 3L));
        eventStore.insert(new Event("joAo", 4L));
        eventStore.insert(new Event("Maria", 5L));
        eventStore.insert(new Event("mAria", 6L));
        eventStore.insert(new Event("mar íA", 7L));
        eventStore.insert(new Event("ma riã", 8L));

        if(eventStore.moveNext()){
            assertEquals(8, (int) eventStore.currentSize);
        }

        eventStore.close();
    }

    @Test
    public void removeFromAType() throws Exception{ 
        EventStoreImplementation eventStore = new EventStoreImplementation();
        eventStore.insert(new Event("joAo", 1L));
        eventStore.insert(new Event("João", 2L));
        eventStore.insert(new Event("joaO", 3L));
        eventStore.insert(new Event("joAo", 4L));
        eventStore.insert(new Event("Maria", 5L));
        eventStore.insert(new Event("mAria", 6L));
        eventStore.insert(new Event("mar íA", 7L));
        eventStore.insert(new Event("ma riã", 8L));

        eventStore.removeAll("maria");

        if(eventStore.moveNext()){
            assertEquals(4, (int) eventStore.currentSize);
        }

        eventStore.close();
    }

    @Test
    public void retrievesIterator() throws Exception{ 
        EventStoreImplementation eventStore = new EventStoreImplementation();
        eventStore.insert(new Event("joAo", 1L));
        eventStore.insert(new Event("João", 2L));
        eventStore.insert(new Event("joaO", 3L));
        eventStore.insert(new Event("joAo", 4L));
        eventStore.insert(new Event("Maria", 5L));
        eventStore.insert(new Event("mAria", 6L));
        eventStore.insert(new Event("mar íA", 7L));
        eventStore.insert(new Event("ma riã", 8L));
        
        EventIterator iterator = eventStore.query("joao", 1L, 4L);
        
        assertEquals(3, (int) eventStore.filteredEvents.size());

        eventStore.close();
    }
}