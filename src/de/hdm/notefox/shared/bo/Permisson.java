package de.hdm.notefox.shared.bo;

public class Permisson {
	


	  /**
	   * Variablen von Permission.
	  */

	private int permissionId;
	private String permissionName = "";
	

	  
	/**Auslesen und Setzen der Variablen
	 */
	
	public int getPermissionId() {
		return permissionId;
	}
	
	public void setPermissionId(int permissionId) {
		this.permissionId = permissionId;
	}
	
	public String getPermissionName() {
		return permissionName;
	}
	
	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	}

}
