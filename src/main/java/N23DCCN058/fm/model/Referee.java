
package N23DCCN058.fm.model;

import java.util.Objects;


public class Referee {
    private Integer refereeId;
    private String refereeName, phoneNumber;

    public Referee() {
    }

    public Referee(String refereeName, String phoneNumber) {
        this.refereeName = refereeName;
        this.phoneNumber = phoneNumber;
    }

    public Referee(Integer refereeId, String refereeName, String phoneNumber) {
        this.refereeId = refereeId;
        this.refereeName = refereeName;
        this.phoneNumber = phoneNumber;
    }

    public Integer getRefereeId() {
        return refereeId;
    }

    public void setRefereeId(Integer refereeId) {
        this.refereeId = refereeId;
    }

    public String getRefereeName() {
        return refereeName;
    }

    public void setRefereeName(String refereeName) {
        this.refereeName = refereeName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "" + this.refereeName + " | " + this.phoneNumber;
    }
    
    public Object[] toObjectArray(){
        return new Object[]{this.refereeId, this.refereeName, this.phoneNumber};
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.refereeId);
        hash = 83 * hash + Objects.hashCode(this.refereeName);
        hash = 83 * hash + Objects.hashCode(this.phoneNumber);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Referee other = (Referee) obj;
        if (!Objects.equals(this.refereeName, other.refereeName)) {
            return false;
        }
        if (!Objects.equals(this.phoneNumber, other.phoneNumber)) {
            return false;
        }
        return Objects.equals(this.refereeId, other.refereeId);
    }
    
    
    
}
