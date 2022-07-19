package net.intelie.challenges;

import java.text.Normalizer;
import java.util.LinkedList;

public class EventStoreImplementation implements EventStore, EventIterator {

    private LinkedList<Event> events = new LinkedList<Event>();
    public LinkedList<Event> filteredEvents = new LinkedList<Event>();
    private int currentIndex = -1;
    public int currentSize;
    
    @Override
    public synchronized void insert(Event event) {
        this.events.add(event);
    }

    public static String removeSpecialCharacters(String str) {
        return Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
    }

    @Override
    public synchronized void removeAll(String type) {
        this.events.removeIf(events -> removeSpecialCharacters(type).replaceAll(" ", "").toLowerCase().equals(removeSpecialCharacters(events.getType()).replaceAll(" ", "").toLowerCase()));
    }

    @Override
    public synchronized EventIterator query(String type, long startTime, long endTime) {
        
        if(moveNext()){
            Event e = this.current();
            if(removeSpecialCharacters(type).replaceAll(" ", "").toLowerCase()
            .equals(removeSpecialCharacters(e.getType()).replaceAll(" ", "").toLowerCase())){
                if((e.getTimestamp()>=startTime) && (e.getTimestamp()<endTime)){
                    filteredEvents.add(e);
                }
            }
            this.query(type, startTime, endTime);
        }
        return new EventStoreImplementation();
    }


    @Override
    public synchronized boolean moveNext(){
        currentSize = events.size();
        if (currentIndex<currentSize-1){
            currentIndex++;
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public synchronized Event current(){
        return this.events.get(this.currentIndex);
    }

    @Override
    public synchronized void remove(){
        this.events.remove(this.currentIndex);
    }

    @Override
    public void close() throws Exception {
        this.events.clear();
    }
}