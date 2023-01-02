package tb.soft;

public class Person2 extends Person implements Comparable{

    public Person2(Person p) throws PersonException {
        super(p.getFirstName(), p.getLastName());
        setBirthYear(p.getBirthYear());
        setJob(p.getJob());
    }

    @Override
    public boolean equals(Object obj) {

        if(this == obj) return true;
        if(obj == null || getClass() != obj.getClass()) return false;

        Person2 p = (Person2) obj;

        if(this.getFirstName().equals(p.getFirstName()))
            if(this.getLastName().equals(p.getLastName()))
                if(this.getBirthYear() == p.getBirthYear())
                    return this.getJob() == p.getJob();
        return false;
    }

    @Override
    public int hashCode() {
        final int hashPrime = 97;
        int result = getFirstName().hashCode();
        result = result * hashPrime + getLastName().hashCode();
        result = result * hashPrime + getBirthYear();
        return result * hashPrime + getJob().hashCode();
    }

    @Override
    public int compareTo(Object o) {
        return hashCode()-o.hashCode();
    }

}
