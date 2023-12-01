import React, { useState } from 'react';

const SamlMetadataBuilder = () => {
  const [metadata, setMetadata] = useState('');
  const [entityId, setEntityId] = useState('');
  const [acsUrl, setAcsUrl] = useState('');
  const [certificate, setCertificate] = useState('');
  const [nameIdFormat, setNameIdFormat] = useState('urn:oasis:names:tc:SAML:1.1:nameid-format:unspecified');
  const [logoutUrl, setLogoutUrl] = useState('');
  const [authnRequestSigned, setAuthnRequestSigned] = useState(false);
  const [wantAssertionsSigned, setWantAssertionsSigned] = useState(false);

  const downloadMetadata = () => {
    const blob = new Blob([metadata], { type: 'text/xml' });
    const url = URL.createObjectURL(blob);
    const a = document.createElement('a');
    a.href = url;
    a.download = 'metadata.xml';
    document.body.appendChild(a);
    a.click();
    document.body.removeChild(a);
    URL.revokeObjectURL(url);
  };

  return (
    <div className="container">
      <h2>SAML SP Metadata Builder</h2>
      <div className="form">
        <label>
          Entity ID:
          <input type="text" value={entityId} onChange={(e) => setEntityId(e.target.value)} />
        </label>
        <label>
          ACS URL:
          <input type="text" value={acsUrl} onChange={(e) => setAcsUrl(e.target.value)} />
        </label>
        <label>
          Certificate (optional):
          <textarea value={certificate} onChange={(e) => setCertificate(e.target.value)} />
        </label>
        <label>
          Name ID Format:
          <select value={nameIdFormat} onChange={(e) => setNameIdFormat(e.target.value)}>
            <option value="urn:oasis:names:tc:SAML:1.1:nameid-format:unspecified">Unspecified</option>
            {/* Add other options as needed */}
          </select>
        </label>
        <label>
          Logout URL (optional):
          <input type="text" value={logoutUrl} onChange={(e) => setLogoutUrl(e.target.value)} />
        </label>
        <label>
          AuthnRequest Signed:
          <input type="checkbox" checked={authnRequestSigned} onChange={() => setAuthnRequestSigned(!authnRequestSigned)} />
        </label>
        <label>
          Want Assertions Signed:
          <input type="checkbox" checked={wantAssertionsSigned} onChange={() => setWantAssertionsSigned(!wantAssertionsSigned)} />
        </label>
        <button onClick={() => setMetadata(`
          <EntityDescriptor entityID="${entityId}">
            <SPSSODescriptor protocolSupportEnumeration="urn:oasis:names:tc:SAML:2.0:protocol">
              <NameIDFormat>${nameIdFormat}</NameIDFormat>
              <AssertionConsumerService Binding="urn:oasis:names:tc:SAML:2.0:bindings:HTTP-POST" Location="${acsUrl}" />
              ${logoutUrl && `<SingleLogoutService Binding="urn:oasis:names:tc:SAML:2.0:bindings:HTTP-Redirect" Location="${logoutUrl}" />`}
              ${authnRequestSigned && '<AuthnRequestsSigned>true</AuthnRequestsSigned>'}
              ${wantAssertionsSigned && '<WantAssertionsSigned>true</WantAssertionsSigned>'}
              ${certificate && `<KeyDescriptor use="signing"><KeyInfo><X509Data><X509Certificate>${certificate}</X509Certificate></X509Data></KeyInfo></KeyDescriptor>`}
            </SPSSODescriptor>
          </EntityDescriptor>
        `)}>Build Metadata</button>
        <button onClick={downloadMetadata}>Download Metadata</button>
      </div>
      <div className="result">
        <h3>Generated Metadata:</h3>
        <pre>{metadata}</pre>
      </div>
    </div>
  );
};

export default SamlMetadataBuilder;
