package eu.balsick.android.fantaplayers.comm.internal;

/**
 * Created by balsi on 09/04/2016.
 */
public class FragmentRequest<C extends FragmentRequestListener.RequestType> {
    public C type;

    public FragmentRequestCallbackHandler callbackHandler;

    public FragmentRequest(C object, FragmentRequestCallbackHandler callbackHandler) {
        this.type = object;
        this.callbackHandler = callbackHandler;
    }
    public FragmentRequest(C object) {
        this(object, null);
    }

}
