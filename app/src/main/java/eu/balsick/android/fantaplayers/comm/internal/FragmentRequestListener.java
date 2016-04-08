package eu.balsick.android.fantaplayers.comm.internal;

/**
 * Created by balsi on 07/04/2016.
 */
public interface FragmentRequestListener {

    public void fragmentRequestHappened(FragmentRequest request);

    class FragmentRequest {
        String request;

    }
}
