//
// Copyright (c) ZeroC, Inc. All rights reserved.
//
//
// Ice version 3.7.3
//
// <auto-generated>
//
// Generated from file `iot.ice'
//
// Warning: do not edit this file.
//
// </auto-generated>
//

package iot;

public class DeviceNotActive extends com.zeroc.Ice.UserException
{
    public DeviceNotActive()
    {
    }

    public DeviceNotActive(Throwable cause)
    {
        super(cause);
    }

    public String ice_id()
    {
        return "::iot::DeviceNotActive";
    }

    /** @hidden */
    @Override
    protected void _writeImpl(com.zeroc.Ice.OutputStream ostr_)
    {
        ostr_.startSlice("::iot::DeviceNotActive", -1, true);
        ostr_.endSlice();
    }

    /** @hidden */
    @Override
    protected void _readImpl(com.zeroc.Ice.InputStream istr_)
    {
        istr_.startSlice();
        istr_.endSlice();
    }

    /** @hidden */
    public static final long serialVersionUID = 4448426671703858900L;
}
