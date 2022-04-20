package com.example.tadreess.model;

import org.simpleframework.xml.Element;

public class MyPojo {

    private LoginResults loginResults;

    public LoginResults getLoginResults ()
    {
        return loginResults;
    }

    public void setLoginResults (LoginResults loginResults)
    {
        this.loginResults = loginResults;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [loginResults = "+loginResults+"]";
    }
    public class LoginResults
    {
        private String Status;

        private String xmlns;

        private Result Result;

        public String getStatus ()
        {
            return Status;
        }

        public void setStatus (String Status)
        {
            this.Status = Status;
        }

        public String getXmlns ()
        {
            return xmlns;
        }

        public void setXmlns (String xmlns)
        {
            this.xmlns = xmlns;
        }

        public Result getResult ()
        {
            return Result;
        }

        public void setResult (Result Result)
        {
            this.Result = Result;
        }

        @Override
        public String toString()
        {
            return "ClassPojo [Status = "+Status+", xmlns = "+xmlns+", Result = "+Result+"]";
        }
    }
    public class Result
    {
        private LoginResult loginResult;

        public LoginResult getLoginResult ()
        {
            return loginResult;
        }

        public void setLoginResult (LoginResult loginResult)
        {
            this.loginResult = loginResult;
        }

        @Override
        public String toString()
        {
            return "ClassPojo [loginResult = "+loginResult+"]";
        }
        public class LoginResult
        {
            private String UserId;

            private String IsShedule;

            private String StageId;

            private String UserStatus;

            public String getUserId ()
            {
                return UserId;
            }

            public void setUserId (String UserId)
            {
                this.UserId = UserId;
            }

            public String getIsShedule ()
            {
                return IsShedule;
            }

            public void setIsShedule (String IsShedule)
            {
                this.IsShedule = IsShedule;
            }

            public String getStageId ()
            {
                return StageId;
            }

            public void setStageId (String StageId)
            {
                this.StageId = StageId;
            }

            public String getUserStatus ()
            {
                return UserStatus;
            }

            public void setUserStatus (String UserStatus)
            {
                this.UserStatus = UserStatus;
            }

            @Override
            public String toString()
            {
                return "ClassPojo [UserId = "+UserId+", IsShedule = "+IsShedule+", StageId = "+StageId+", UserStatus = "+UserStatus+"]";
            }
        }
    }
}
