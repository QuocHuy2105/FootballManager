
package N23DCCN058.fm.model;


public enum TeamRole {
    AWAY,
    HOME;
    
    public static TeamRole fromString(String role){
        if(role == null) return null;
        return TeamRole.valueOf(role.toUpperCase());
    }
}
