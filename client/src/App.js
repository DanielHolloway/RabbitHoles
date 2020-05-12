import React, { useState, useEffect } from 'react';
// import logo from './logo.svg';
import './App.css';
import axios from 'axios';

function App() {
  // const [count, setCount] = useState(0);

  // // Similar to componentDidMount and componentDidUpdate:
  // useEffect(() => {
  //   // Update the document title using the browser API
  //   document.title = `You clicked ${count} times`;
  // });

  // return (
  //   <div>
  //     <p>You clicked {count} times</p>
  //     <button onClick={() => setCount(count + 1)}>
  //       Click me
  //     </button>
  //   </div>
  // );

  const [data, setData] = useState({ articles: [] });
 
  async function fetchData() {
    const result = await axios('/articles/search/Captain%20America');
    //console.log(result);
    setData(data => ({ ...data, articles: result.data }));
    //setData(result.data);
  }

  useEffect(() => {
    fetchData();
  }, []);


 
  return (
    // <p>
    //   {data.articles}
    // </p>
    <ul>
      {data.articles.map(item => (
        <li key={item.id}>
          <a href={item.link}>{item.title}</a>
        </li>
      ))}
    </ul>
  );

  // return (
  //   <div className="App">
  //     <header className="App-header">
  //       <img src={logo} className="App-logo" alt="logo" />
  //       <p>
  //         Edit <code>src/App.js</code> and save to reload.
  //       </p>
  //       <a
  //         className="App-link"
  //         href="https://reactjs.org"
  //         target="_blank"
  //         rel="noopener noreferrer"
  //       >
  //         Learn React
  //       </a>
  //     </header>
  //   </div>
  // );
}

export default App;
