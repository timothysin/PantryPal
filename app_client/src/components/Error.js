import { useLocation } from "react-router-dom";

function Error() {

  const location = useLocation();

  return <div>
    {location.state && location.state.message}
    {!location.state &&
      <div>
        <h1>404</h1>
        <p>Page not found</p>
      </div>}
  </div>

}

export default Error;