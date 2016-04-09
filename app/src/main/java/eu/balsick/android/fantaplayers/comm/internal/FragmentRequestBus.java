package eu.balsick.android.fantaplayers.comm.internal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by balsick on 08/04/2016.
 */
public class FragmentRequestBus {

    private static FragmentRequestBus current;
    private Map<Class<? extends eu.balsick.android.fantaplayers.comm.internal.FragmentRequestListener.RequestType>, List<FragmentRequestListener>> listeners = new HashMap<>();
    private Map<FragmentRequest, FragmentRequestCallbackHandler> callbackHandlerMap;

    private FragmentRequestBus(){
    }

    public <T extends FragmentRequestListener.RequestType> void registerListenerForRequest(Class<T> clazz, FragmentRequestListener<T> listener){
        if (!listeners.containsKey(clazz))
            listeners.put(clazz, new ArrayList<FragmentRequestListener>());
        listeners.get(clazz).add(listener);
    }

    public static FragmentRequestBus getCurrent() {
        if (current == null)
            current = new FragmentRequestBus();
        return current;
    }

    public void request(FragmentRequest request){
        request(request, null);
    }
    public void request(FragmentRequest request, FragmentRequestCallbackHandler callbackHandler) {
        if (request == null)
            return;
        Class<? extends FragmentRequestListener.RequestType> c = request.type.getClass();
        if (!listeners.containsKey(c))
            return;
        if (callbackHandlerMap == null)
            callbackHandlerMap = new HashMap<>();
        callbackHandlerMap.put(request, callbackHandler);
        for (FragmentRequestListener listener : listeners.get(c))
            listener.fragmentRequestHappened(request);
    }

    public void callback(FragmentResult result){
        FragmentRequest request = result.getRequest();
        FragmentRequestCallbackHandler callbackHandler = callbackHandlerMap.remove(request);
        if (callbackHandler != null)
            callbackHandler.handle(result);
    }
}
