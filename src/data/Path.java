package src.data;

public class Path {
    int from;//zacatek cesty (zaroven take konec cesty, protoze pokud vede cesta z mista x do y, tak take z y do x)
    int to;//konec cesty(a zaroven i zacatek, viz komentar vyse)

    public Path(int from, int to) {
        this.from = from;
        this.to = to;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }

    @Override
    public String toString() {
        return "Path{" +
                "from=" + from +
                ", to=" + to +
                '}';
    }
}