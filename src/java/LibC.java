import com.sun.jna.Native;

public class LibC {

    static {
       Native.register("c");
    }

    public static native int setuid(int uid);

}