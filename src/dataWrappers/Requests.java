package src.dataWrappers;

import src.data.Counters;
import src.data.Request;

import java.util.LinkedList;

public class Requests {
    LinkedList<Request> requests;//spojovy seznam pozadavku
    Counters counters;//pocet
    int reqToDo = 0;//pozadavky, ktere se musi udelat
    int reqInAction = 0;//pozadavky, ktere se prave vyrizuji
    int reqCantBeDone = 0;//pozadavky, ktere nemohou byt vyrizeny
    int reqDone = 0;//hotove pozadavky
    public Requests(LinkedList<Request> requests, Counters counters) {
        this.requests = requests;
        this.counters = counters;
        this.reqToDo = requests.size();//pocatecni nastaveni pozadavku
    }
    public void reqreqDoneIncrement(){
        reqDone++;
    }

    public void reqCantBeDoneIncrement(){
        reqCantBeDone++;
    }

    public void setReqInActionDecrement(){
        reqInAction--;
    }

    public void setReqInActionIncrement(){
        reqInAction++;
    }

    public void reqToDoDecrement(){
        reqToDo--;
    }

    public void reqToDoIncrement(){
        reqToDo++;
    }

    public LinkedList<Request> getRequests(){
        return requests;
    }

    public Counters getCounters() {
        return counters;
    }

    public int getReqToDo() {
        return reqToDo;
    }

    public int getReqInAction() {
        return reqInAction;
    }

    public int getReqCantBeDone() {
        return reqCantBeDone;
    }

    public int getReqDone() {
        return reqDone;
    }

    public Request getRequestById(int i){return requests.get(i);}
}