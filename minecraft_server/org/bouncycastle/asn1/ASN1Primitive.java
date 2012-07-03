package org.bouncycastle.asn1;

public abstract class ASN1Primitive extends ASN1Object
{
    ASN1Primitive()
    {
    }

    public final boolean equals(Object par1Obj)
    {
        if (this == par1Obj)
        {
            return true;
        }
        else
        {
            return (par1Obj instanceof ASN1Encodable) && func_56446_a(((ASN1Encodable)par1Obj).func_56445_a());
        }
    }

    public ASN1Primitive func_56445_a()
    {
        return this;
    }

    public abstract int hashCode();

    abstract boolean func_56446_a(ASN1Primitive asn1primitive);
}
