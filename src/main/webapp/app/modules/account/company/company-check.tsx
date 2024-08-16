import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import '../../../../content/css/CompanyCheckForm.css'; // Adjust the path if needed

const CompanyCheckForm = () => {
  const [companyName, setCompanyName] = useState('');
  const [error, setError] = useState<string | null>(null);
  const navigate = useNavigate();

  const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    setError(null);

    try {
      const response = await axios.get('/api/check-company', {
        params: { companyName },
      });
      // Debugging: Log the response to check its structure
      console.log(response.data);

      if (response.data === 'redirect:/login') {
        navigate('/login');
      } else if (response.data === 'redirect:/account/register') {
        navigate('/account/register');
      } else {
        setError('Unexpected response from server.');
      }
    } catch (error) {
      setError('Failed to check company name. Please try again.');
    }
  };

  return (
    <div>
      <h2 className="unique-company-check-header">Check Company</h2>
      <form onSubmit={handleSubmit} className="unique-company-check-form">
        <div>
          <label htmlFor="companyName" className="unique-company-check-label">
            Company Name:
          </label>
          <input
            type="text"
            id="companyName"
            value={companyName}
            onChange={e => setCompanyName(e.target.value)}
            required
            className="unique-company-check-input"
          />
        </div>
        <button type="submit" className="unique-company-check-button">
          Submit
        </button>
        {error && <p className="unique-company-check-error">{error}</p>}
      </form>
    </div>
  );
};

export default CompanyCheckForm;
