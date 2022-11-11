import { Typography } from "@mui/material";
import Box from "@mui/material/Box";
import EmojiIcon from "@mui/icons-material/SentimentDissatisfied";

function NotFoundPage() {
  return (
    <Box display="flex" flexDirection="column" alignItems="center">
      <Box mt={10}>
        <EmojiIcon style={{"fontSize": "200px"}}/>
      </Box>

      <Typography m={5} variant="h1" component="h1">
        404
      </Typography>
      <Typography variant="h5" component="h5">
        Page Not Found
      </Typography>
    </Box>
  );
}

export default NotFoundPage;
