//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.mao.cn.learnRxJava2.callBack;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class XsonObject {
    private JSONObject jsonObject;

    public XsonObject() {
        this.jsonObject = new JSONObject();
    }

    public XsonObject(String res) throws JSONException {
        this.jsonObject = new JSONObject(res);
    }

    public boolean contains(String key, String value) {
        if(this.jsonObject == null) {
            return false;
        } else if(!this.jsonObject.has(key)) {
            return false;
        } else {
            try {
                return this.jsonObject.getString(key).contains(value);
            } catch (JSONException var4) {
                return false;
            }
        }
    }

    public boolean equals(String key, String value) {
        if(key == null) {
            return false;
        } else if(this.jsonObject == null) {
            return false;
        } else if(!this.jsonObject.has(key)) {
            return false;
        } else {
            try {
                return this.jsonObject.getString(key).equals(value);
            } catch (JSONException var4) {
                return false;
            }
        }
    }

    public boolean equals(String key, boolean value) {
        if(key == null) {
            return false;
        } else if(this.jsonObject == null) {
            return false;
        } else if(!this.jsonObject.has(key)) {
            return false;
        } else {
            try {
                return this.jsonObject.getBoolean(key) == value;
            } catch (JSONException var4) {
                return false;
            }
        }
    }

    public boolean equals(String key, long value) {
        if(key == null) {
            return false;
        } else if(this.jsonObject == null) {
            return false;
        } else if(!this.jsonObject.has(key)) {
            return false;
        } else {
            try {
                return this.jsonObject.getLong(key) == value;
            } catch (JSONException var5) {
                return false;
            }
        }
    }

    public boolean equals(String key, int value) {
        if(key == null) {
            return false;
        } else if(this.jsonObject == null) {
            return false;
        } else if(!this.jsonObject.has(key)) {
            return false;
        } else {
            try {
                return this.jsonObject.getInt(key) == value;
            } catch (JSONException var4) {
                return false;
            }
        }
    }

    public String getString(String key) {
        if(key == null) {
            return null;
        } else if(this.jsonObject == null) {
            return null;
        } else if(!this.jsonObject.has(key)) {
            return null;
        } else {
            try {
                return this.jsonObject.getString(key);
            } catch (JSONException var3) {
                return null;
            }
        }
    }

    public long getLong(String key) {
        if(key == null) {
            return 0L;
        } else if(this.jsonObject == null) {
            return 0L;
        } else if(!this.jsonObject.has(key)) {
            return 0L;
        } else {
            try {
                return this.jsonObject.getLong(key);
            } catch (JSONException var3) {
                return 0L;
            }
        }
    }

    public int getInt(String key) {
        if(key == null) {
            return 0;
        } else if(this.jsonObject == null) {
            return 0;
        } else if(!this.jsonObject.has(key)) {
            return 0;
        } else {
            try {
                return this.jsonObject.getInt(key);
            } catch (JSONException var3) {
                return 0;
            }
        }
    }

    public boolean getBoolean(String key) {
        return this.getBoolean(key, false);
    }

    public boolean getBoolean(String key, boolean def) {
        if(key == null) {
            return def;
        } else if(this.jsonObject == null) {
            return def;
        } else if(!this.jsonObject.has(key)) {
            return def;
        } else {
            try {
                return this.jsonObject.getBoolean(key);
            } catch (JSONException var4) {
                return def;
            }
        }
    }

    public JSONArray getArray(String key) {
        if(key == null) {
            return null;
        } else if(this.jsonObject == null) {
            return null;
        } else if(!this.jsonObject.has(key)) {
            return null;
        } else {
            try {
                return this.jsonObject.getJSONArray(key);
            } catch (JSONException var3) {
                return null;
            }
        }
    }

    public String toString() {
        return this.jsonObject != null?this.jsonObject.toString():null;
    }
}
