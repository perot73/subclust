package se.pellpin.subclust.source;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import se.pellpin.subclust.Point;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.util.ArrayList;
import java.util.List;

public class GPXSource implements Source {

    String filename;

    public GPXSource(String filename) {
        this.filename = filename;
    }

    @Override
    public ArrayList<Point> get() {

        GPXHandler gpxHandler = new GPXHandler();

        try {
            SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser saxParser = spf.newSAXParser();
            XMLReader xmlReader = saxParser.getXMLReader();
            xmlReader.setContentHandler(gpxHandler);
            xmlReader.parse(filename);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ArrayList<Point> points = new ArrayList<Point>();
        for (Track track : gpxHandler.getTracks()) {
            for (Segment segment : track.segments) {
                for (Point point : segment.points) {
                    points.add(point);
                }
            }
        }
        return points;
    }


    public class GPXHandler extends DefaultHandler {

        ArrayList<Track> tracks = new ArrayList<Track>();

        private Track trk;
        private Segment seg;
        private Point pnt;
        private StringBuilder characters = new StringBuilder();

        public List<Track> getTracks() {
            return this.tracks;
        }

        @Override
        public void startDocument() throws SAXException {
            super.startDocument();
        }

        @Override
        public void endDocument() throws SAXException {
            super.endDocument();
        }

        @Override
        public void startElement(String namespaceURI, String localName,
                                 String qName, Attributes attributes) throws SAXException {
            if ("trk".equalsIgnoreCase(qName)) {
                trk = new Track();
            } else if ("trkseg".equalsIgnoreCase(qName)) {
                seg = new Segment();
            } else if ("trkpt".equalsIgnoreCase(qName)) {
                pnt = new Point(
                        Double.parseDouble(attributes.getValue("lon")),
                        Double.parseDouble(attributes.getValue("lat"))
                );
                seg.add(pnt);
            } else if ("time".equalsIgnoreCase(qName)) {
                characters.setLength(0);
            }
        }

        @Override
        public void characters(char[] chars, int start, int length) throws SAXException {
            characters.append(chars, start, length);
        }

        @Override
        public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
            if ("trk".equalsIgnoreCase(qName)) {
                tracks.add(trk);
            } else if ("trkseg".equalsIgnoreCase(qName)) {
                trk.addSegment(seg);
            } else if ("trkpt".equalsIgnoreCase(qName)) {
                seg.add(pnt);
            } else if ("time".equalsIgnoreCase(qName)) {
                /*
                try {
                    SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssz");
                    Date dDate = fmt.parse(characters.toString());
                    pnt.time = dDate;
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                */
            }
        }
    }

    public static class Track {
        String name;
        String desc;
        ArrayList<Segment> segments;

        public void addSegment(Segment seg) {
            if (segments == null) {
                segments = new ArrayList<Segment>();
            }
            segments.add(seg);
        }
    }

    public static class Segment {
        ArrayList<Point> points;

        public void add(Point pnt) {
            if (points == null) {
                points = new ArrayList<Point>();
            }
            points.add(pnt);
        }
    }

}