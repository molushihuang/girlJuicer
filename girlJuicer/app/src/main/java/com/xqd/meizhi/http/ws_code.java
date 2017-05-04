package com.xqd.meizhi.http;


public enum ws_code {

	UNKNOWN(-2),
	NOLOGIN(-1), //未登录
	SUCCESS(0), //成功
	CONFLICT(1),//冲突
	FAIL(100), //失败
	FAIL_IN_TEAM(101), //失败 已经在团队或者群聊中
	HAS_PAYED(110), //失败 已经在团队或者群聊中
	UPDATE(9),
	DELETE(10),
	ROLE_CHANGE(-11),
	NONET(1000),
	SLOWNET(1001),
	JSONERROR(1100);
	  

	int value;
	ws_code(int value){
		this.value = value;
	}
	
	
	public static ws_code parse(int value){
		
		switch (value) {
			case 0:
				return SUCCESS;
			case 1:
				return CONFLICT;
			case -1:
				return NOLOGIN;
			case 9:
				return UPDATE;
			case 10:
				return DELETE;
			case -11:
				return ROLE_CHANGE;
			case 100:
				return FAIL;
			case 101:
				return FAIL_IN_TEAM;
			case 110:
				return HAS_PAYED;
			case 1000:
				return NONET;
			case 1100:
				return JSONERROR;
				// 待定
			default:
				return UNKNOWN;
		}
	}
}
