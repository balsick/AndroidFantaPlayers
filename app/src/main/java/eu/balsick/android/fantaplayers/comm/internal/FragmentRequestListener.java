package eu.balsick.android.fantaplayers.comm.internal;

/**
 * Created by balsi on 07/04/2016.
 */
public interface FragmentRequestListener<T extends FragmentRequestListener.RequestType> {

    void fragmentRequestHappened(FragmentRequest<T> request);

    interface RequestType{

    }

}
