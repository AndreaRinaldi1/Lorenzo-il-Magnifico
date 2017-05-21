package core;
import cards.*;

public class GameBoardProva {
	private static CouncilPrivilege councilPrivilege;

	public static CouncilPrivilege getCouncilPrivilege() {
		return councilPrivilege;
	}

	public static void setCouncilPrivilege(CouncilPrivilege councilPrivilege) {
		GameBoardProva.councilPrivilege = councilPrivilege;
	}
	
}
