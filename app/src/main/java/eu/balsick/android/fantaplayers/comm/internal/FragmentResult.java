package eu.balsick.android.fantaplayers.comm.internal;

/**
 * Created by balsi on 09/04/2016.
 */
public class FragmentResult {

    Object result;
    private FragmentRequest request;

    public FragmentResult(FragmentRequest request, Object object) {
        this.request = request;
        this.result = object;
    }

    public FragmentRequest getRequest() {
        return request;
    }

    public Object getResult() {
        return result;
    }

}
