
package N23DCCN058.fm.model;

public enum PlayerRole {
    STARTING,
    SUBSTITUTE;
    
    public static PlayerRole fromString(String role){
        if(role == null) return null;
        return PlayerRole.valueOf(role.toUpperCase());
    }
}
