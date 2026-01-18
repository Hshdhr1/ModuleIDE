package android.support.v4.app;

import android.app.Notification;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes6.dex */
public interface INotificationSideChannel extends IInterface {
    void cancel(String str, int i, String str2) throws RemoteException;

    void cancelAll(String str) throws RemoteException;

    void notify(String str, int i, String str2, Notification notification) throws RemoteException;

    public static class Default implements INotificationSideChannel {
        public void notify(String packageName, int id, String tag, Notification notification) throws RemoteException {
        }

        public void cancel(String packageName, int id, String tag) throws RemoteException {
        }

        public void cancelAll(String packageName) throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements INotificationSideChannel {
        private static final String DESCRIPTOR = "android.support.v4.app.INotificationSideChannel";
        static final int TRANSACTION_cancel = 2;
        static final int TRANSACTION_cancelAll = 3;
        static final int TRANSACTION_notify = 1;

        public Stub() {
            attachInterface(this, "android.support.v4.app.INotificationSideChannel");
        }

        public static INotificationSideChannel asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface("android.support.v4.app.INotificationSideChannel");
            if (iin != null && (iin instanceof INotificationSideChannel)) {
                return (INotificationSideChannel) iin;
            }
            return new Proxy(obj);
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            Notification _arg3;
            switch (code) {
                case 1:
                    data.enforceInterface("android.support.v4.app.INotificationSideChannel");
                    String _arg0 = data.readString();
                    int _arg1 = data.readInt();
                    String _arg2 = data.readString();
                    if (data.readInt() != 0) {
                        _arg3 = (Notification) Notification.CREATOR.createFromParcel(data);
                    } else {
                        _arg3 = null;
                    }
                    notify(_arg0, _arg1, _arg2, _arg3);
                    return true;
                case 2:
                    data.enforceInterface("android.support.v4.app.INotificationSideChannel");
                    String _arg02 = data.readString();
                    int _arg12 = data.readInt();
                    String _arg22 = data.readString();
                    cancel(_arg02, _arg12, _arg22);
                    return true;
                case 3:
                    data.enforceInterface("android.support.v4.app.INotificationSideChannel");
                    String _arg03 = data.readString();
                    cancelAll(_arg03);
                    return true;
                case 1598968902:
                    reply.writeString("android.support.v4.app.INotificationSideChannel");
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements INotificationSideChannel {
            public static INotificationSideChannel sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return "android.support.v4.app.INotificationSideChannel";
            }

            public void notify(String packageName, int id, String tag, Notification notification) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("android.support.v4.app.INotificationSideChannel");
                    _data.writeString(packageName);
                    _data.writeInt(id);
                    _data.writeString(tag);
                    if (notification != null) {
                        _data.writeInt(1);
                        notification.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(1, _data, (Parcel) null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().notify(packageName, id, tag, notification);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void cancel(String packageName, int id, String tag) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("android.support.v4.app.INotificationSideChannel");
                    _data.writeString(packageName);
                    _data.writeInt(id);
                    _data.writeString(tag);
                    boolean _status = this.mRemote.transact(2, _data, (Parcel) null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().cancel(packageName, id, tag);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void cancelAll(String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("android.support.v4.app.INotificationSideChannel");
                    _data.writeString(packageName);
                    boolean _status = this.mRemote.transact(3, _data, (Parcel) null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().cancelAll(packageName);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(INotificationSideChannel impl) {
            if (Proxy.sDefaultImpl != null) {
                throw new IllegalStateException("setDefaultImpl() called twice");
            }
            if (impl != null) {
                Proxy.sDefaultImpl = impl;
                return true;
            }
            return false;
        }

        public static INotificationSideChannel getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
