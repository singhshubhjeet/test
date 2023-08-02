# test
import React, { useEffect } from 'react';
import examplePDF from './assets/example.pdf'; // Import the file

const AutoDownloadComponent = () => {
  useEffect(() => {
    const downloadFile = async () => {
      try {
        const response = await fetch(examplePDF); // Fetch the file
        const blob = await response.blob();
        const blobUrl = URL.createObjectURL(blob);

        const link = document.createElement('a');
        link.href = blobUrl;
        link.download = 'example.pdf'; // Set the desired filename and extension
        document.body.appendChild(link);
        link.click();
        document.body.removeChild(link);

        // Clean up the URL object
        URL.revokeObjectURL(blobUrl);
      } catch (error) {
        console.error('Error downloading file:', error);
      }
    };

    downloadFile();
  }, []); // Run the effect only once

  return <div>Downloading...</div>;
};

export default AutoDownloadComponent;
