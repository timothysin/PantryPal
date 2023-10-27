function ValidationSummary({ errors = [] }) {
    if (!errors || errors.length === 0) {
      return null;
    }
  
    return (
      <div className="alert alert-danger">
        <h5>Failed due to the following:</h5>
        <ul>
          {errors.map((err, index) => (
            <li key={index}>{err}</li>
          ))}
        </ul>
      </div>
    );
  }
  
  export default ValidationSummary;