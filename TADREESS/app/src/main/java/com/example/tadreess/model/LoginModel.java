package com.example.tadreess.model;

public class LoginModel {
    private String Status;
    private LoginResult loginResult;



    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public LoginResult getLoginResult() {
        return loginResult;
    }

    public void setLoginResult(LoginResult loginResult) {
        this.loginResult = loginResult;
    }

    class LoginResult {
        private String UserId;
        private String UserStatus;
        private String StageId;
        private String IsShedule;



        public String getUserId() {
            return UserId;
        }

        public void setUserId(String userId) {
            UserId = userId;
        }

        public String getUserStatus() {
            return UserStatus;
        }

        public void setUserStatus(String userStatus) {
            UserStatus = userStatus;
        }

        public String getStageId() {
            return StageId;
        }

        public void setStageId(String stageId) {
            StageId = stageId;
        }

        public String getIsShedule() {
            return IsShedule;
        }

        public void setIsShedule(String isShedule) {
            IsShedule = isShedule;
        }
    }
}
