import React, { useState } from 'react';

const SamlMetadataBuilder = () => {
  const [metadata, setMetadata] = useState('');
  const [entityId, setEntityId] = useState('https://your-app-entity-id');
  const [acsUrl, setAcsUrl] = useState('https://your-app-acs-url');
  const [certificate, setCertificate] = useState(''); // Optional parameter
  const [nameIdFormat, setNameIdFormat] = useState('urn:oasis:names:tc:SAML:1.1:nameid-format:unspecified');
  const [logoutUrl, setLogoutUrl] = useState(''); // Optional parameter
  const [authnRequestSigned, setAuthnRequestSigned] = useState(false);
  const [wantAssertionsSigned, setWantAssertionsSigned] = useState(false);

  const buildMetadata = () => {
    const metadataTemplate = `
      <EntityDescriptor entityID="${entityId}">
        <SPSSODescriptor protocolSupportEnumeration="urn:oasis:names:tc:SAML:2.0:protocol">
          <NameIDFormat>${nameIdFormat}</NameIDFormat>
          <AssertionConsumerService Binding="urn:oasis:names:tc:SAML:2.0:bindings:HTTP-POST" Location="${acsUrl}" />
          ${logoutUrl && `<SingleLogoutService Binding="urn:oasis:names:tc:SAML:2.0:bindings:HTTP-Redirect" Location="${logoutUrl}" />`}
          ${authnRequestSigned && `<AuthnRequestsSigned>true</AuthnRequestsSigned>`}
          ${wantAssertionsSigned && `<WantAssertionsSigned>true</WantAssertionsSigned>`}
          <KeyDescriptor>
            <KeyInfo>
              <X509Data>
                <X509Certificate>${certificate}</X509Certificate>
              </X509Data>
            </KeyInfo>
          </KeyDescriptor>
        </SPSSODescriptor>
      </EntityDescriptor>
    `;

    setMetadata(metadataTemplate);
  };

  const downloadMetadata = () => {
    const blob = new Blob([metadata], { type: 'application/xml' });
    const link = document.createElement('a');
    link.href = window.URL.createObjectURL(blob);
    link.download = 'saml-metadata.xml';
    document.body.appendChild(link);
    link.click();
    document.body.removeChild(link);
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
          <input type="text" value={nameIdFormat} onChange={(e) => setNameIdFormat(e.target.value)} />
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
        <button onClick={buildMetadata}>Build Metadata</button>
        {metadata && <button onClick={downloadMetadata}>Download Metadata</button>}
      </div>
      <div className="result">
        <h3>Generated Metadata:</h3>
        <pre>{metadata}</pre>
      </div>
    </div>
  );
};

export default SamlMetadataBuilder;
