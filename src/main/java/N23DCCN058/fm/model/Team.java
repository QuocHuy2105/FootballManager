
package N23DCCN058.fm.model;


public class Team {
    private Integer teamId;
    private String teamName, department, coachName;

    public Team() {
    }

    public Team(String teamName, String department, String coachName) {
        this.teamName = teamName;
        this.department = department;
        this.coachName = coachName;
    }
    
    public Team(Integer teamId, String teamName, String department, String coachName) {
        this.teamId = teamId;
        this.teamName = teamName;
        this.department = department;
        this.coachName = coachName;
    }

    public Integer getTeamId() {
        return teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getCoachName() {
        return coachName;
    }

    public void setCoachName(String coachName) {
        this.coachName = coachName;
    }

    @Override
    public String toString() {
        return teamName;
    }
    
    public Object[] toObjectArray(){
        return new Object[]{this.teamId, this.teamName, this.department, this.coachName};
    }
    
}
