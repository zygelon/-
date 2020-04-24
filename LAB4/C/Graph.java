package C;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Graph {

    private class Cell {
        int StartTown;
        int Destination;

        public Cell(int StartTown, int Destination) {
            this.StartTown = StartTown;
            this.Destination = Destination;
        }
    }

    private ArrayList<String> cities;
    private ArrayList<ArrayList<Integer>> graph;

    private ReadWriteLock rwLock;

    private Lock readLock;
    private Lock writeLock;

    public Graph(ArrayList<String> cities, ArrayList<ArrayList<Integer>> graph) {
        this.cities = cities;
        this.graph = graph;
        this.rwLock = new ReentrantReadWriteLock();
        this.readLock = rwLock.readLock();
        this.writeLock = rwLock.writeLock();
    }



    public List<String> getCities() {
        return cities;
    }


    private Cell findCities(String cityFrom, String cityTo) {
        Cell retData = new Cell(-1, -1);
        for(int i = 0; i < cities.size(); ++i) {
            if(cities.get(i).equals(cityFrom)) {
                retData.StartTown = i;
            }

            if(cities.get(i).equals(cityTo)) {
                retData.Destination = i;
            }

            if(retData.StartTown != -1 && retData.Destination != -1) {
                break;
            }
        }

        return retData;
    }

    public Graph() {
        this.cities = new ArrayList<>();
        this.graph = new ArrayList<>();
        this.rwLock = new ReentrantReadWriteLock();
        this.readLock = rwLock.readLock();
        this.writeLock = rwLock.writeLock();
    }

    public void changePrice(String cityFrom, String cityTo, int newPrice) {
        writeLock.lock();
        Cell cell = findCities(cityFrom, cityTo);
        if(cell.StartTown == -1 || cell.Destination == -1) {
            writeLock.unlock();
            return;
        }
        graph.get(cell.StartTown).set(cell.Destination, newPrice);
        graph.get(cell.Destination).set(cell.StartTown, newPrice);
        writeLock.unlock();
    }

    public void setCities(ArrayList<String> cities) {
        this.cities = cities;
    }

    public ArrayList<ArrayList<Integer>> getGraph() {
        return graph;
    }

    public void setGraph(ArrayList<ArrayList<Integer>> graph) {
        this.graph = graph;
    }


    public void changeTrip(String cityFrom, String cityTo, int newPrice) {
        changePrice(cityFrom, cityTo, newPrice);
    }



    public void debugShowGraph() {
        readLock.lock();
        System.out.println("Cities: " + cities);

        for (ArrayList<Integer> integers : graph) {
            System.out.println(integers);
        }
        readLock.unlock();
    }

    public void appendCity(String city) {
        writeLock.lock();
        cities.add(city);

        for (ArrayList<Integer> integers : graph) {
            integers.add(0);
        }

        ArrayList<Integer> list = new ArrayList<>(cities.size());
        for(int i = 0; i < cities.size(); ++i) {
            list.add(0);
        }

        graph.add(list);
        writeLock.unlock();
    }

    public boolean isTrip(String cityFrom, String cityTo) {
        readLock.lock();
        Cell inst = findCities(cityFrom, cityTo);

        if(inst.StartTown == -1 || inst.Destination == -1) {
            readLock.unlock();
            return false;
        }

        if(graph.get(inst.StartTown).get(inst.Destination) > 0) {
            readLock.unlock();
            return true;
        }

        readLock.unlock();
        return false;
    }
}
